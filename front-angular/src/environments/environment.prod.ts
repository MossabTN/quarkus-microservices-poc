import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
let keycloakConfig: KeycloakConfig = {
  url: 'http://192.168.99.100:32698/auth',
  realm: 'local',
  clientId: 'front'
};

export const environment = {
  production: true,
  assets: {
    dotaImages:
      'https://cdn-keycloak-angular.herokuapp.com/assets/images/dota-heroes/'
  },
  apis: { dota: 'http://localhost:3000' },
  keycloak: keycloakConfig
};
