helm install --name kong stable/kong -n kong -f kong-values.yaml
kubectl apply -f konga.yml