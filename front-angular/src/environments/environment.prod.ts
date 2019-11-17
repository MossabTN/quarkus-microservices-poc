import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
let keycloakConfig: KeycloakConfig = {
  url: 'http://localhost:9080/auth',
  realm: 'quarkus',
  clientId: 'front'
};

export const environment = {
  production: true,
  apis: { users: 'http://localhost:8080' },
  keycloak: keycloakConfig
};
