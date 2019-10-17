/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Supe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrgDBDao implements OrgDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Org getOrgById(int id) throws DaoException{
        String getOrgById = "Select * from Organizations where Id = ?";

        return template.queryForObject(getOrgById, new OrgMapper(), id);
    }

    @Override
    public List<Org> getAllOrgs() throws DaoException{
        String getOrgs = "Select * from Organizations";

        return template.query(getOrgs, new OrgMapper());
    }

    @Override
    public Org addOrg(Org org) throws DaoException{
        String insert = "INSERT INTO Organizations(Name, Description, Contact) VALUES(?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            toReturn.setString(1, org.getName());
            toReturn.setString(2, org.getDescription());
            toReturn.setString(3, org.getContact());
            return toReturn;
        };

        template.update(psc, holder);
        int id = holder.getKey().intValue();
        org.setId(id);
//        insertOrgAndSupes(org);
        return org;

    }

    @Override
    public void updateOrg(Org org) throws DaoException{
        String updateOrg = "UPDATE Organizations SET name = ?, description = ?, "
                + "Contact = ? WHERE id = ?";
        template.update(updateOrg,
                org.getName(),
                org.getDescription(),
                org.getContact(),
                org.getId());
        final String deleteSupeInOrg = "delete from SupersInOrganizations where OrganizationId = ?";
        template.update(deleteSupeInOrg, org.getId());
        insertOrgAndSupes(org);
    }

    @Override
    public void deleteOrg(int id) throws DaoException{
        String deleteSupeOrg = "Delete from SupersInOrganizations where OrganizationId = ?";
        template.update(deleteSupeOrg, id);

        String deleteOrg = "delete from Organizations where id = ?";
        template.update(deleteOrg, id);
    }

    @Override
    public List<Org> getOrgsForSupe(Supe supe) throws DaoException{
        String getOrgsQuery = "Select o.* from Organizations o "
                + "inner join SupersInOrganizations so on o.Id = so.OrganizationId  "
                + "where so.SuperId = ?";

        return template.query(getOrgsQuery, new OrgMapper(), supe.getId());
    }

    private void insertOrgAndSupes(Org org) throws DaoException{
        String insertOrgs = "Insert into SupersInOrganizations"
                + "(SuperId, OrganizationID) values (?,?)";
        for (Supe supe : org.getAllSupers()) {
            template.update(insertOrgs, supe.getId(), org.getId());
        }
    }

    private static class OrgMapper implements RowMapper<Org> {

        @Override
        public Org mapRow(ResultSet rs, int i) throws SQLException {
            Org org = new Org();
            org.setId(rs.getInt("Id"));
            org.setName(rs.getString("Name"));
            org.setDescription(rs.getString("Description"));
            org.setContact(rs.getString("Contact"));

            return org;
        }
    }

}
