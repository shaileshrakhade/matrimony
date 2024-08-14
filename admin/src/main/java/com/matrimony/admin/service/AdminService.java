package com.matrimony.admin.service;

import com.matrimony.admin.customExceptions.exceptions.AdminAttributesNotFoundException;
import com.matrimony.admin.dao.AdminDao;

import java.util.List;

public interface AdminService {
    AdminDao add(AdminDao adminDao);

    AdminDao show(String key) throws AdminAttributesNotFoundException;

    List<AdminDao> showAll();

    AdminDao update(AdminDao adminDao) throws AdminAttributesNotFoundException;

    AdminDao bioDataPdfUrl() throws AdminAttributesNotFoundException;

    AdminDao dashBoardMessage() throws AdminAttributesNotFoundException;

    AdminDao notificationMessage() throws AdminAttributesNotFoundException;

    boolean isPublish() throws AdminAttributesNotFoundException;

    AdminDao makePublish(boolean isPublish) throws AdminAttributesNotFoundException;
}
