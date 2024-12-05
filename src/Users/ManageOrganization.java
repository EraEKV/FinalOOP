package Users;

import System.Organization;

public interface ManageOrganization {

    void deleteOrganization(Organization org);
    void joinOrganization(Organization org);
    void createOrganization(String name);
    void leaveOrganization(Organization org);

}
