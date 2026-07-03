# Explore StateName — Tourism Information Website (DevOps Assignment 2, Use Case 5)

A state tourism website (Home, Attractions, Hotels, Travel Guides & Maps,
Book a Trip) built as a single **Java 25 + Spring Boot 4.1 (Maven)**
application, deployed with a full DevOps workflow sized for holiday-season
traffic spikes:

**Git → Jenkins (CI/CD, Maven build) → Docker → Kubernetes (3 replicas + optional autoscaling) → Nagios + Graphite + Grafana (monitoring)**

Structured the same way as your `taskmanager` / `college-event-site`
projects — one Maven project at the repo root.

---

## 1. Project Structure

```
tourism-site/
├── pom.xml                        # Maven build file (Spring Boot 4.1, Java 25)
├── Dockerfile                     # Builds a JDK 25 image around the Maven jar
├── Jenkinsfile                    # CI/CD pipeline: Maven build → Docker → K8s
├── .gitignore
├── src/
│   ├── main/java/com/tourism/app/
│   │   ├── TourismApplication.java
│   │   ├── controller/
│   │   │   ├── AttractionController.java     # GET  /api/attractions
│   │   │   ├── HotelController.java          # GET  /api/hotels
│   │   │   ├── BookingController.java        # POST /api/bookings
│   │   │   └── HealthController.java         # GET  /healthz
│   │   ├── service/
│   │   │   ├── AttractionService.java
│   │   │   ├── HotelService.java
│   │   │   └── BookingService.java
│   │   └── model/
│   │       ├── Attraction.java
│   │       ├── Hotel.java
│   │       └── Booking.java
│   ├── main/resources/
│   │   ├── application.properties            # server.port=8082, actuator on 9091
│   │   └── static/                            # index.html, attractions.html, hotels.html,
│   │                                           # travel-guides.html, booking.html, css/, js/
│   └── test/java/...                          # JUnit test (Spring context load)
├── k8s/
│   ├── deployment.yaml                        # 3 replicas, resource limits, probes
│   ├── service.yaml                           # NodePort 30082 (app), 30091 (actuator)
│   └── hpa.yaml                                # Optional autoscaler (3→8 pods on CPU load)
└── monitoring/
    ├── docker-compose.yml                     # Nagios + Graphite + Grafana, one project
    ├── nagios/objects/tourism-site.cfg
    ├── grafana/tourism-dashboard.json
    └── scripts/
        └── push_metrics_to_graphite.ps1        # Windows PowerShell metrics pusher
```

---

## 2. Prerequisites (install once)

- Git
- **Java 25 (JDK)** and **Maven** — check with `java -version` and `mvn -version`
- Docker Desktop, with **Kubernetes enabled**
- `kubectl`
- Jenkins (local Windows install, pipeline uses `bat` steps)
- A GitHub account

---

## 3. Step 1 — Push to GitHub

```bash
cd tourism-site
git init
git add .
git commit -m "Initial commit - Tourism Information Website DevOps project"
git branch -M main
git remote add origin https://github.com/<your-username>/tourism-site.git
git push -u origin main
```

Also update the repo URL inside `Jenkinsfile` (`Checkout Source Code` stage)
to match your actual GitHub repo.

---

## 4. Step 2 — Build with Maven and run locally (sanity check)

```bash
mvn clean package -DskipTests
java -jar target/tourism-0.0.1-SNAPSHOT.jar
```

Open:
- **http://localhost:8082** — Home page
- **http://localhost:8082/api/attractions** — JSON list
- **http://localhost:8082/api/hotels** — JSON list
- **http://localhost:9091/actuator/health** — `{"status":"UP"}`

Stop with `Ctrl+C`.

---

## 5. Step 3 — Build the Docker image and test it

```bash
docker build -t tourism-site:v1 .
docker run -d -p 8082:8082 -p 9091:9091 --name tourism-site tourism-site:v1
```

Open **http://localhost:8082** — take a screenshot.

```bash
docker stop tourism-site
docker rm tourism-site
```

---

## 6. Step 4 — Deploy to Kubernetes

```bash
kubectl get nodes
docker build -t tourism-site:v1 .
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl get pods
kubectl get svc
```

Open **http://localhost:30082**.

> If the NodePort URL doesn't load but pods show `Running`, this is a known
> Docker Desktop-on-Windows quirk. Test with
> `kubectl port-forward svc/tourism-site 8082:8082` first to confirm the
> app itself is healthy, then try **Docker Desktop → Settings → Kubernetes
> → Restart Kubernetes Cluster**.

### Optional: enable autoscaling (demonstrates "scalable hosting")

```bash
kubectl apply -f k8s/hpa.yaml
kubectl get hpa
```
This requires the `metrics-server` add-on — see the comments inside
`k8s/hpa.yaml` for the one-time install command if `TARGETS` shows
`<unknown>`. If you'd rather keep it simple, skip this — the fixed 3-replica
Deployment on its own already satisfies "Kubernetes Pods in Running state".

Screenshot `kubectl get pods` (3 pods Running) and the browser view.

---

## 7. Step 5 — Jenkins CI/CD

1. Open Jenkins → New Item → Pipeline → name it `tourism-site-pipeline`.
2. Pipeline → Definition → "Pipeline script from SCM" → Git → your repo URL
   → Script Path: `Jenkinsfile`.
3. Click **Build Now**.

The `Jenkinsfile` uses `bat` steps and assumes `java`, `mvn`, `docker`, and
`kubectl` are on your system PATH.

Take screenshots of: Jenkins Dashboard, job configuration, Console Output of
a successful build, and the green build status.

---

## 8. Step 6 — Monitoring: Nagios + Graphite + Grafana

```bash
cd monitoring
docker compose up -d
```

| Service | URL | Login |
|---|---|---|
| Nagios | http://localhost:8084/nagios | nagiosadmin / nagios |
| Graphite | http://localhost:8090 | — |
| Grafana | http://localhost:3001 | admin / admin |

> Ports here are deliberately different from the college-event-site
> monitoring stack (8083/8089/3000) so both can run at the same time without
> clashing, if you're keeping both projects around.

### Push metrics into Graphite

From **PowerShell**:
```powershell
cd monitoring\scripts
powershell -ExecutionPolicy Bypass -File push_metrics_to_graphite.ps1
```
Check Graphite's metrics tree (`tourism_site → system` / `→ app`) at
http://localhost:8090 — screenshot it.

### Set up Grafana

1. http://localhost:3001 → log in `admin`/`admin` → set a new password.
2. **Connections → Data sources → Add data source → Graphite** → URL:
   `http://graphite:80` → **Save & Test**.
3. **Dashboards → New → Import** → upload
   `monitoring/grafana/tourism-dashboard.json` → pick the Graphite data
   source → **Import**.
4. Screenshot the dashboard (CPU, Memory, Network, Uptime, HTTP Availability
   panels — matching the assignment's exact requirement list).

### Check Nagios

Open http://localhost:8084/nagios → **Services**. You should see host
`tourism-site-host` with `HTTP - Website Availability` and `PING` services.
Give it 1–2 minutes, then screenshot the Service Status Details page (Host
UP, Services OK).

---

## 9. Step 7 — Cleanup

```bash
kubectl delete -f k8s/hpa.yaml --ignore-not-found
kubectl delete -f k8s/service.yaml
kubectl delete -f k8s/deployment.yaml
cd monitoring
docker compose down
```

---

## 10. Assignment Checklist Mapping

| Requirement | Where it's satisfied |
|---|---|
| Collaborative source code management | Git repository (Step 1) |
| Automated deployment workflow | `Jenkinsfile` pipeline (Step 5) |
| Docker-based hosting | `Dockerfile` (Step 3) |
| Kubernetes deployment | `k8s/deployment.yaml` (3 replicas), `k8s/service.yaml` (Step 4) |
| Website accessible through browser | http://localhost:30082 (NodePort Service) |
| Docker container running successfully | Step 3 screenshot |
| Kubernetes Pods in Running state | Step 4 screenshot |
| NodePort Service configured | `k8s/service.yaml` (30082/30091) |
| Nagios reporting website availability | `HTTP - Website Availability` service (Step 6) |
| Graphite storing performance metrics | `push_metrics_to_graphite.ps1` (Step 6) |
| Grafana: CPU, Memory, Network, Uptime dashboards | `tourism-dashboard.json` (Step 6) |

---

## 11. Customization Ideas (optional polish)

- Replace "StateName" with your actual state/region name throughout the
  HTML files and this README.
- Swap the placeholder `images/*.jpg` references in `AttractionService.java`
  for real image files placed under `src/main/resources/static/images/`.
- Replace the generic Google Maps embed query in `travel-guides.html` with
  actual coordinates for your attractions.
