import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
let keycloakConfig: KeycloakConfig = {
  url: 'http://localhost:8180/auth',
  realm: 'local',
  clientId: 'front'
};

export const environment = {
  production: false,
  apis: {
    customer: 'http://localhost:8000/customer',
    notification: 'http://localhost:8000/notification',
    product: 'http://localhost:8000/product',
    order: 'http://localhost:8000/order'
  },
  keycloak: keycloakConfig
};
