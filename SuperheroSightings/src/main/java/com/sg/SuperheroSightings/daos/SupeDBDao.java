/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Location;
import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Power;
import com.sg.SuperheroSightings.dtos.Supe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tamara
 */
@Repository
public class SupeDBDao implements SupeDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Supe getSupeById(int id) throws DaoException{
        try {
            String byIdQuery = "SELECT s.Id, s.Name, s.Description, p.Id as PId, "
                    + "p.Name as PName  FROM Supers s "
                    + "inner join Powers p on s.PowerId = p.Id "
                    + "WHERE s.Id = ?";
            Supe supe = template.queryForObject(byIdQuery, new SuperMapper(), id);

            return supe;
        } catch (DataAccessException ex) {
            throw new DaoException("Unable to connect to database");
        }
    }

    @Override
    public List<Supe> getAllSupes() throws DaoException{
        String getAllQuery = "SELECT s.Id, s.Name, s.Description, p.Id as PId, "
                + "p.Name as PName \n FROM Supers s \n"
                + "inner join Powers p on s.PowerId = p.Id";
        return template.query(getAllQuery, new SuperMapper());
    }

    @Override
    public Supe addSupe(Supe supe) throws DaoException{

        String insert = "INSERT INTO Supers(Name, Description, PowerId) VALUES(?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            toReturn.setString(1, supe.getName());
            toReturn.setString(2, supe.getDescription());
            toReturn.setInt(3, supe.getSuperpower().getId());
            return toReturn;
        };

        template.update(psc, holder);
        int id = holder.getKey().intValue();
        supe.setId(id);
        insertSupeInOrg(supe);
        return supe;
    }

    @Override
    public void updateSupe(Supe supe) throws DaoException{
        String updateSupe = "UPDATE Supers SET name = ?, description = ?, "
                + "PowerId = ? WHERE id = ?";
        template.update(updateSupe,
                supe.getName(),
                supe.getDescription(),
                supe.getSuperpower().getId(),
                supe.getId());
        String deleteSupeInOrg = "delete from SupersInOrganizations where SuperId = ?";
        template.update(deleteSupeInOrg, supe.getId());
        insertSupeInOrg(supe);
    }

    @Override
    public void deleteSupe(int id) throws DaoException{
        String deleteSupeOrg = "Delete from SupersInOrganizations where SuperId = ?";
        template.update(deleteSupeOrg, id);

        String deleteSupeSight = "Delete from SupersPerSightings where SuperId = ?";
        template.update(deleteSupeSight, id);

        String deleteSupe = "delete from Supers where id = ?";
        template.update(deleteSupe, id);
    }

    @Override
    public List<Supe> getSupesByLoc(Location location) throws DaoException{
        String supesLocQuery = "SELECT s.Id, s.Name, s.Description, p.Id as PId, "
                + "p.Name as PName FROM Supers s \n"
                + "inner join Powers p on s.PowerId = p.Id \n"
                + "inner join SupersPerSightings sps on s.Id = sps.SuperId \n"
                + "inner join Sightings si on sps.SightingId = si.Id \n"
                + " WHERE si.LocationId = ?";
        return template.query(supesLocQuery, new SuperMapper(), location.getId());
    }

    @Override
    public List<Supe> getSupesByOrg(Org organization) throws DaoException{
        String supesOrgQuery = "SELECT s.Id, s.Name, s.Description, "
                + "p.Id as PId, p.Name as PName FROM Supers s \n"
                + "inner join Powers p on s.PowerId = p.Id \n"
                + "inner join SupersInOrganizations so on s.Id = so.SuperId\n"
                + "where so.OrganizationId = ?";
        return template.query(supesOrgQuery, new SuperMapper(), organization.getId());
    }

    private void insertSupeInOrg(Supe supe) throws DaoException{
        String insertOrgs = "Insert into SupersInOrganizations"
                + "(SuperId, OrganizationId) values (?,?)";
        for (Org orgs : supe.getOrganizations()) {
            template.update(insertOrgs, supe.getId(), orgs.getId());
        }
    }

    private static class SuperMapper implements RowMapper<Supe> {

        @Override
        public Supe mapRow(ResultSet rs, int i) throws SQLException {

            Supe newSuper = new Supe();
            newSuper.setId(rs.getInt("Id"));
            newSuper.setName(rs.getString("Name"));
            newSuper.setDescription(rs.getString("Description"));

            Power power = new Power();
            power.setId(rs.getInt("PId"));
            power.setName(rs.getString("PName"));

            newSuper.setSuperpower(power);
            return newSuper;

        }

    }

}
