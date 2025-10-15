package com.codeup.libronova.service.implement;

import com.codeup.libronova.dao.implement.PartnerDAOImpl;
import com.codeup.libronova.domain.Partner;
import com.codeup.libronova.exception.PartnerServiceException;
import com.codeup.libronova.service.PartnerService;
import com.codeup.libronova.validation.implement.PartnerValidationImpl;

import javax.swing.*;
import java.util.List;

    public class PartnerServiceImpl implements PartnerService {
        private final PartnerDAOImpl dao;
        private final PartnerValidationImpl validation;

        public  PartnerServiceImpl(PartnerDAOImpl dao, PartnerValidationImpl validation){
            this.validation = validation;
            this.dao = dao;
        }

        @Override
        public void addNewPartner(Partner partner) {
            if(partner == null){
                throw new PartnerServiceException("Partners cannot be empty" + JOptionPane.WARNING_MESSAGE);
            }
            if(!validation.isValidCode(partner.getCode_partner())){
                throw new PartnerServiceException("Invalid code, partner code cannot be empty" + JOptionPane.ERROR_MESSAGE);
            }
            if(!validation.isValidFullname(partner.getFullname())){
                throw new PartnerServiceException("Invalid code, partner name cannot be empty" + JOptionPane.ERROR_MESSAGE);
            }
            if(!validation.isValidPhonenumber(partner.getPhonenumber())){
                throw new PartnerServiceException("Invalid code, partner phone cannot be empty" + JOptionPane.ERROR_MESSAGE);
            }
            if(!validation.isValidStatus()){
                throw new PartnerServiceException("Invalid code, partner status cannot be empty" + JOptionPane.ERROR_MESSAGE);
            }

            dao.addNewPartner(partner);
        }

        @Override
        public Partner findById(int id) {
            if (id <= 0) {
                throw new PartnerServiceException("Invalid code, id does not exist" + JOptionPane.ERROR_MESSAGE);
            }

            Partner partner = dao.findById(id);
            if (partner == null) {
                throw new PartnerServiceException("Partners cannot be empty" + JOptionPane.WARNING_MESSAGE);
        }
            return partner;
        }

        @Override
        public List<Partner> listAllPartner() {
            return dao.listAllPartner();
        }

        @Override
        public boolean updatePartnerPhone(Partner partner) {
            if (partner == null) {
                throw new PartnerServiceException("Partners cannot be empty" + JOptionPane.ERROR_MESSAGE);
            }

            if (!validation.isValidPhonenumber(partner.getPhonenumber())) {
                throw new PartnerServiceException("Invalid code, partner phone cannot be empty" + JOptionPane.ERROR_MESSAGE);
            }

                Partner existingProduct = dao.findById(partner.getId());
                if (existingProduct == null) {
                    throw new PartnerServiceException("Invalid code, id does not exist" + JOptionPane.ERROR_MESSAGE);
                }
                dao.updatePartnerPhone(partner);
            return false;
        }

        @Override
        public void deletePartner(int id) {
            if(id <= 0){
                throw new PartnerServiceException("Invalid code, id does not exist" + JOptionPane.ERROR_MESSAGE);
            }
        }
    }
