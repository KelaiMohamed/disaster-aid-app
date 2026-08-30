export const environment = {
  production: true,
  // Replaced at Docker build time (see Dockerfile) from the GATEWAY_URL
  // build arg. Was previously '${GATEWAY_URL}' in single quotes, which is
  // NOT a template literal in TypeScript/JS - it was a literal 19-character
  // string, so every API call in the app was hitting a URL that could
  // never exist. Fixed by substituting this placeholder at build time
  // instead of relying on JS string interpolation that never happened.
  gatewayUrl: '__GATEWAY_URL__',
};
