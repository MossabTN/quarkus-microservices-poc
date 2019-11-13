helm repo add codecentric https://codecentric.github.io/helm-charts
kubectl apply -f keycloak-realm-config.yml
helm install --name keycloak -n keycloak codecentric/keycloak -f keycloak-value.yml
