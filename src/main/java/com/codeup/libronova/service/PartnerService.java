package com.codeup.libronova.service;

import com.codeup.libronova.domain.Partner;

import java.util.List;

public interface PartnerService {
    void addNewPartner(Partner partner);
    Partner findById(int id);
    List<Partner> listAllPartner();
    boolean updatePartnerPhone(Partner partner);
    void deletePartner(int id);
}
