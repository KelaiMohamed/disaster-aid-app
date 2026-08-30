# ---- Build stage ----
FROM node:20 AS build-stage
WORKDIR /app

COPY package*.json /app/
RUN npm install --legacy-peer-deps

COPY ./ /app/

# Backend gateway URL and Google Maps API key are baked in at build time
# (Angular is a static SPA - there's no server-side process to read
# environment variables from at container runtime). Override with
# --build-arg when building a different environment.
#
# Security note: the Google Maps key was previously hardcoded directly in
# index.html and committed to source control. Treat that old key as
# exposed - rotate/restrict it in the Google Cloud Console regardless of
# this fix. Going forward, pass a real key via GOOGLE_MAPS_API_KEY at
# build time; it is never committed here.
ARG GATEWAY_URL=http://localhost:8080
ARG GOOGLE_MAPS_API_KEY=""
RUN sed -i "s#__GATEWAY_URL__#${GATEWAY_URL}#g" src/environments/environment.ts \
 && sed -i "s#__GOOGLE_MAPS_API_KEY__#${GOOGLE_MAPS_API_KEY}#g" src/index.html

ARG configuration=production
RUN npm run build -- --output-path=./dist/out --configuration $configuration

# ---- Runtime stage ----
FROM nginx:alpine
COPY --from=build-stage /app/dist/out/ /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
