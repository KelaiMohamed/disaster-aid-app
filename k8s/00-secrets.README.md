# Secrets are intentionally NOT committed as a plain YAML file - a
# committed secrets.yml is exactly the mistake this repo's history already
# made once (see the root README's "Fixes applied" section). Create the
# secret directly instead:
#
#   kubectl create secret generic disaster-aid-secrets \
#     --from-literal=DB_PASSWORD='<a-real-password>' \
#     --from-literal=JWT_SECRET="$(openssl rand -base64 32)"
#
# Every Deployment in this folder reads DB_PASSWORD and JWT_SECRET from
# that secret via secretKeyRef - see e.g. auth-service.yml.
#
# If you'd rather manage this as a YAML file (for GitOps / ArgoCD /
# Flux), generate one locally and keep it OUT of git:
#
#   kubectl create secret generic disaster-aid-secrets \
#     --from-literal=DB_PASSWORD='<a-real-password>' \
#     --from-literal=JWT_SECRET="$(openssl rand -base64 32)" \
#     --dry-run=client -o yaml > secrets.local.yml
#
# secrets.local.yml matches a pattern already covered by the repo's
# .gitignore (*.local.yml) - it will not be committed even if you forget
# to exclude it yourself.
