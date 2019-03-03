package ismapp.iitism.cyberlabs.com.ismapp.club.clubdetails.view;

import ismapp.iitism.cyberlabs.com.ismapp.club.clubdetails.model.ClubDetails;
import ismapp.iitism.cyberlabs.com.ismapp.club.clubdetails.model.MemberListResponse;

public interface ClubDetailsView {
    void showProgressbar(boolean show);
    void showmodel(ClubDetails clubDetails);
    void showMessage(String msg);
    void showMemberList(MemberListResponse memberListResponse);
}