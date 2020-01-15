/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Location;
import com.sg.SuperheroSightings.dtos.Sighting;
import com.sg.SuperheroSightings.dtos.Supe;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tamara
 */
@Repository
public class SightingDBDao implements SightingDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Sighting getSightingById(int id) throws DaoException {
        String getSightingById = "Select s.*, l.Name as LName, l.Description as LDes, "
                + "l.Address as LAd, l.Latitude as LLat, l.Longitude as LLo "
                + "from Sightings s inner join "
                + "Locations l on s.LocationId = l.Id where s.Id = ?";

        return template.queryForObject(getSightingById, new SightingMapper(), id);

    }

    @Override
    public List<Sighting> getAllSightings() throws DaoException {
        String getSighting = "Select s.*, l.Name as LName, l.Description as LDes, "
                + "l.Address as LAd, l.Latitude as LLat, l.Longitude as LLo "
                + "from Sightings s inner join "
                + "Locations l on s.LocationId = l.Id";

        return template.query(getSighting, new SightingMapper());
    }

    @Override
    public Sighting addSighting(Sighting sighting) throws DaoException {

        String insert = "INSERT INTO Sightings (LocationId, SightDate) VALUES(?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            toReturn.setInt(1, sighting.getLocation().getId());
            Date date = java.sql.Date.valueOf(sighting.getDate());
            toReturn.setDate(2, date);
            return toReturn;
        };

        template.update(psc, holder);
        int id = holder.getKey().intValue();
        sighting.setId(id);
        insertSupersPerSighting(sighting);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) throws DaoException {

        final String updateSighting = "UPDATE sightings SET LocationId = ?, SightDate = ? "
                + "WHERE id = ?";
        template.update(updateSighting,
                sighting.getLocation().getId(),
                sighting.getDate(),
                sighting.getId());
        final String deleteSuperSightings = "delete from SupersPerSightings where SightingId = ?";
        template.update(deleteSuperSightings, sighting.getId());
        insertSupersPerSighting(sighting);

    }

    @Override
    public void deleteSighting(int id) throws DaoException {

        String deleteSupeSight = "Delete from SupersPerSightings where SightingId = ?";
        template.update(deleteSupeSight, id);

        String deleteSight = "delete from Sightings where id = ?";
        template.update(deleteSight, id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) throws DaoException {

        if (date == null) {
            throw new DaoException("Date cannot be empty.");
        }

        String getSightList = "Select s.*, l.Name as LName, l.Description as LDes, "
                + "l.Address as LAd, l.Latitude as LLat, l.Longitude as LLo "
                + "from Sightings s inner join "
                + "Locations l on s.LocationId = l.Id where s.SightDate = ?";
        Date sDate = java.sql.Date.valueOf(date);
        return template.query(getSightList, new SightingMapper(), sDate);
    }

    private void insertSupersPerSighting(Sighting sighting) throws DaoException {
        String insertSupesSight = "Insert into SupersPerSightings"
                + "(SuperId, SightingId) values (?,?)";
        for (Supe supe : sighting.getSupers()) {
            template.update(insertSupesSight, supe.getId(), sighting.getId());
        }
    }

    @Override
    public List<Location> getAllLocations() throws DaoException {
        String getLocs = "Select * from Locations";

        return template.query(getLocs, new LocationMapper());
    }

    @Override
    public Location getLocById(int id) throws DaoException {
        String getLoc = "Select * from Locations where Id = ?";

        return template.queryForObject(getLoc, new LocationMapper(), id);
    }

    @Override
    public Location addLocation(Location loc) throws DaoException {
        String insert = "insert into Locations(Name, Description, Address, Latitude, Longitude) "
                + "values (?, ?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            toReturn.setString(1, loc.getName());
            toReturn.setString(2, loc.getDescription());
            toReturn.setString(3, loc.getAddress());
            toReturn.setBigDecimal(4, loc.getLatitude());
            toReturn.setBigDecimal(5, loc.getLongitude());
            return toReturn;
        };

        template.update(psc, holder);
        int id = holder.getKey().intValue();
        loc.setId(id);

        return loc;
    }

    @Override
    public void deleteLocation(int id) throws DaoException {
        String deleteSupesPerSighting = "delete sps.* from SupersPerSightings sps\n"
                + "inner join Sightings s on sps.SightingId = s.Id\n"
                + "where s.LocationId = ?";
        
        template.update(deleteSupesPerSighting, id);
        
        String deleteSighting = "delete from Sightings s where LocationId = ?";
        
        template.update(deleteSighting, id);
        
        String deleteLoc = "delete from locations where Id = ?";
        template.update(deleteLoc, id);
        
    }

    @Override
    public void updateLocation(Location loc) throws DaoException {
        String updateLoc= "UPDATE Locations SET Name = ?, Description = ?, Address = ?, "
                + "Latitude = ?, Longitude = ? "
                + "WHERE id = ?";
        template.update(updateLoc,
                loc.getName(),
                loc.getDescription(),
                loc.getAddress(),
                loc.getLatitude(),
                loc.getLongitude(),
                loc.getId());

    }

    private static class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sight = new Sighting();
            sight.setId(rs.getInt("Id"));
            Location loc = new Location();
            loc.setId(rs.getInt("LocationId"));
            loc.setName(rs.getString("LName"));
            loc.setDescription(rs.getString("LDes"));
            loc.setAddress(rs.getString("LAd"));
            loc.setLatitude(rs.getBigDecimal("LLat"));
            loc.setLongitude(rs.getBigDecimal("LLo"));
            sight.setLocation(loc);
            LocalDate date = rs.getDate("SightDate").toLocalDate();
            sight.setDate(date);
            return sight;
        }

    }

    private static class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setId(rs.getInt("Id"));
            loc.setName(rs.getString("Name"));
            loc.setDescription(rs.getString("Description"));
            loc.setAddress(rs.getString("Address"));
            loc.setLatitude(rs.getBigDecimal("Latitude"));
            loc.setLongitude(rs.getBigDecimal("Longitude"));
            return loc;
        }
    }

}
