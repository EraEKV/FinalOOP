package Users;

import System.Organization;

/**
 * The ManageOrganization interface defines methods that allow a user to manage their
 * participation in organizations. These actions include joining, creating, and leaving organizations.
 */
public interface ManageOrganization {

    /**
     * Allows a user to join an existing organization.
     * @param org The organization to be joined.
     */
    void joinOrganization(Organization org);

    /**
     * Allows a user to create a new organization.
     * @param name The name of the organization to be created.
     */
    void createOrganization(String name);

    /**
     * Allows a user to leave an organization.
     * @param org The organization to be left.
     */
    void leaveOrganization(Organization org);

}
