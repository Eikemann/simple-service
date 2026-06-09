# Deploying simple-service to Railway

This service is container-ready: a multi-stage `Dockerfile` builds the jar (JDK 21)
and `railway.json` tells Railway to use it, with a health check on
`/actuator/health`. All environment-specific config is read from env vars
(with local-dev fallbacks in `application.yaml`).

## Environment variables

| Variable | Required | Notes |
|---|---|---|
| `PORT` | auto | Injected by Railway; the app binds to it. **Don't set manually.** |
| `SPRING_DATASOURCE_URL` | yes | JDBC URL, e.g. `jdbc:postgresql://host:port/db` |
| `SPRING_DATASOURCE_USERNAME` | yes | DB user |
| `SPRING_DATASOURCE_PASSWORD` | yes | DB password |
| `JWT_SECRET` | yes | Base64 string; must decode to ≥ 32 bytes. Generate one (see below). |
| `JWT_EXPIRATION` | no | Token lifetime in ms (default `86400000` = 24h) |
| `JPA_DDL_AUTO` | no | Default `update`. Use `validate` once schema is stable. |
| `JPA_SHOW_SQL` | no | Set `false` in prod for quieter logs. |

Generate a JWT secret (no spaces or line breaks — paste it as a single line):
```bash
openssl rand -base64 48 | tr -d '\n'
```
The app also accepts a plain passphrase (≥ 32 characters) if it isn't valid Base64.

## Deploy via the Railway dashboard

1. **Push this repo to GitHub** (the `simple-service` repo; the Dockerfile must be
   at its root — it is).
2. **New Project → Deploy from GitHub repo**, pick the repo. Railway detects the
   `Dockerfile` and builds it.
3. **Add a database:** in the project, **New → Database → PostgreSQL**.
4. **Set the service variables** (on the *app* service, not the DB). Use Railway
   references so they track the Postgres service — in the Variables tab:
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://${{Postgres.PGHOST}}:${{Postgres.PGPORT}}/${{Postgres.PGDATABASE}}
   SPRING_DATASOURCE_USERNAME=${{Postgres.PGUSER}}
   SPRING_DATASOURCE_PASSWORD=${{Postgres.PGPASSWORD}}
   JWT_SECRET=<paste the generated secret>
   JPA_SHOW_SQL=false
   ```
   (If your Postgres service is named differently, replace `Postgres` with its name.)
5. **Expose it:** service → **Settings → Networking → Generate Domain**. You'll get
   `https://<something>.up.railway.app`.
6. Railway redeploys; the **health check** waits for `/actuator/health` to return UP.

## Deploy via the Railway CLI (alternative)

```bash
npm i -g @railway/cli
railway login
railway init                 # create/link a project
railway add --database postgres
# set the variables above in the dashboard or with: railway variables --set KEY=VALUE
railway up                   # build & deploy from the Dockerfile
railway domain               # generate a public URL
```

## After deploy

- On first boot the DB is empty, so **`DataSeeder` seeds 8 sample properties**
  (with rooms + amenities). `ddl-auto=update` creates the schema automatically.
- Verify:
  - `GET https://<your-app>.up.railway.app/actuator/health` → `{"status":"UP"}`
  - `GET .../api/properties` → 8 properties
  - Swagger UI: `.../swagger-ui.html`
- **Point the Android app at it:** set `API_BASE_URL` (or the `BASE_URL`
  buildConfig field) to `https://<your-app>.up.railway.app/api/`. HTTPS means you
  can drop `usesCleartextTraffic` for this host.

## Notes

- The Android client uses Retrofit (not a browser), so no CORS config is needed.
  If a web front-end is added later, add a `CorsConfiguration`.
- For production-grade schema management, switch from `ddl-auto=update` to Flyway
  migrations (future work).
