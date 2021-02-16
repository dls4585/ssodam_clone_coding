package ssodam.ssodam.service;

import ssodam.ssodam.domain.Category;

public class AdminServiceImpl implements AdminService{
    @Override
    public Category createCategory(String categoryName) {

        return null;
    }

    @Override
    public void blockMember(Long memberId) {

    }

    @Override
    public boolean loginCheck(Long memberId) {
        return false;
    }
}
