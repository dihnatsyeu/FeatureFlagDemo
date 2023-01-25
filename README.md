# A demo project for a Mettle test task

## Description
This is a sample Restful API for a basic operations on Feature Flags as entity: 
 - create a feature flag (only users with ADMIN role can do that)
 - view a list of feature flags based on 2 criteria: if feature flag is assigned to the user, or it is global

### API

The following endpoints availble:
 - /api/feature_flag GET request - get all the available feature flags either assigned to the user or global
 - /api/feature_flag POST - available only for the admins: creates a feature flag based on the incoming requests
 - /api/feature/flag/account GET - get all the feature flags assigned to the account

### Constraints and Limitation
 Within the Feature Flag entity name should be unique, if global or active attributes are not provided in a request, 
 the default value will be false for both of the attributes.
 Only admins can create feature flags.
 As it's the demo project, there is no User model in DB, hence there is no relation between Feature Flag and user entity.
 Basic authentication is used.

### Environment
 H2 database is used as the datasource.
 Two predefined user accounts are created in memory:
 user1:user1Pass - ROLE_USER
 user2:user2Pass - ROLE_ADMIN



