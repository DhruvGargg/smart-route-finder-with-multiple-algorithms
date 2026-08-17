# Smart Route Finder with Multiple Algorithms

A Spring Boot-based route optimization system that models cities and roads as a weighted graph and finds routes using multiple graph traversal and shortest-path algorithms.

The project compares **BFS, Dijkstra's Algorithm, and A\*** based on route distance, path, and execution time.

---

## 🚀 Features

- Find routes between supported cities
- Compare multiple graph algorithms
- BFS route finding
- Dijkstra's shortest-path algorithm
- A* pathfinding with geographical heuristic
- Weighted graph using an adjacency-list representation
- MySQL database for storing cities and roads
- Browser-based current-location detection
- Finds the nearest supported city using geographical coordinates
- Haversine formula for geographical distance calculation
- Algorithm execution-time comparison
- REST APIs with a Thymeleaf-based frontend
- Validation for unsupported cities and algorithms

---

## 🧠 Algorithms Implemented

### 1. Breadth-First Search (BFS)

BFS explores a graph level by level and minimizes the number of edges traversed.

Since this project uses weighted roads, BFS does **not necessarily produce the minimum-distance route**.

- **Time Complexity:** `O(V + E)`
- **Space Complexity:** `O(V)`

### 2. Dijkstra's Algorithm

Dijkstra's algorithm finds the minimum-weight path between two nodes in a graph with non-negative edge weights.

The implementation uses a `PriorityQueue` to efficiently select the next node with the smallest known distance.

- **Time Complexity:** `O((V + E) log V)`
- **Space Complexity:** `O(V)`

### 3. A* Algorithm

A* combines the actual cost from the source with a heuristic estimate of the remaining distance.

```
f(n) = g(n) + h(n)

Where:
g(n) = distance from the source to the current node
h(n) = estimated distance from the current node to the destination
f(n) = estimated total cost
```

The project uses geographical coordinates of cities to calculate the heuristic. The **Haversine formula** is used for geographical distance calculations.

**Typical Complexity:** Depends on the heuristic and graph structure.

---

## 📊 Algorithm Comparison

Example route from Ghaziabad to Bengaluru:

| Algorithm | Path | Distance |
|---|---|---|
| BFS | Ghaziabad → Delhi → Jaipur → Ahmedabad → Mumbai → Pune → Hyderabad → Bengaluru | 2790 km |
| Dijkstra | Ghaziabad → Noida → Delhi → Jaipur → Ahmedabad → Mumbai → Pune → Hyderabad → Bengaluru | 2787 km |
| A* | Ghaziabad → Noida → Delhi → Jaipur → Ahmedabad → Mumbai → Pune → Hyderabad → Bengaluru | 2787 km |

**Observations:**

- BFS minimizes the number of edges, not the total weighted distance.
- Dijkstra considers edge weights and therefore finds the minimum-distance route.
- A* also finds the optimal route while using geographical information to guide the search.
- Execution time is displayed for comparison, although the graph used in the demonstration is relatively small, so millisecond-level differences should not be considered a meaningful benchmark.

---

## 🏗️ Architecture

```
                    ┌────────────────────┐
                    │      Browser       │
                    │     Thymeleaf      │
                    └─────────┬──────────┘
                              │
                              ▼
                    ┌────────────────────┐
                    │     Controller     │
                    └─────────┬──────────┘
                              │
                              ▼
                    ┌────────────────────┐
                    │    RouteService    │
                    └─────────┬──────────┘
                              │
                  ┌───────────┴───────────┐
                  ▼                       ▼
          ┌───────────────┐       ┌────────────────┐
          │  Repositories │       │   Algorithms   │
          └───────┬───────┘       └───────┬────────┘
                  │                       │
                  ▼                       ▼
          ┌───────────────┐       ┌────────────────┐
          │     MySQL     │       │ BFS / Dijkstra │
          │ Cities / Roads│       │      / A*      │
          └───────────────┘       └────────────────┘
```

---

## 📂 Project Structure

```
src
└── main
    ├── java
    │   └── com.example.smartrouteanddeliveryoptimizer
    │       ├── algorithm
    │       │   ├── Algorithm.java
    │       │   ├── Edge.java
    │       │   ├── RouteResult.java
    │       │   ├── BFSAlgorithm.java
    │       │   ├── DijkstraAlgorithm.java
    │       │   └── AStarAlgorithm.java
    │       │
    │       ├── controller
    │       │   └── RouteController.java
    │       │
    │       ├── dto
    │       │
    │       ├── entity
    │       │   ├── City.java
    │       │   └── Road.java
    │       │
    │       ├── repository
    │       │   ├── CityRepository.java
    │       │   └── RoadRepository.java
    │       │
    │       └── service
    │           └── RouteService.java
    │
    └── resources
        ├── static
        ├── templates
        └── application.properties
```

---

## 🗃️ Database Design

The application uses MySQL.

### Cities

Stores geographical information about supported cities.

```
City
----------------
id
name
latitude
longitude
```

### Roads

Stores connections between cities.

```
Road
----------------
id
source
destination
distance
travel_time
```

`travel_time` is nullable because the current version focuses on distance-based route optimization. It can be used in a future version for fastest-route optimization.

---

## 📍 Current Location

The application can use the browser's Geolocation API to obtain the user's latitude and longitude.

```
Browser
   │
   │ latitude + longitude
   ▼
Spring Boot
   │
   ▼
Find nearest supported city
   │
   ▼
Use nearest city as source
```

The nearest city is determined using the Haversine formula:

```
distance = 2R × asin(√a)
```

where `R` is the Earth's radius.

---

## 🔄 Request Flow

For route finding:

```
User selects source, destination and algorithm
                    │
                    ▼
              RouteController
                    │
                    ▼
              RouteService
                    │
                    ▼
             Load roads from MySQL
                    │
                    ▼
          Build adjacency-list graph
                    │
                    ▼
          Select requested algorithm
                    │
                    ▼
       BFS / Dijkstra / A* execution
                    │
                    ▼
               RouteResult
                    │
                    ▼
              RouteResponse
                    │
                    ▼
                 Browser
```

---

## 🔌 API Endpoints

### Find Route

`POST /api/routes/find`

**Request:**

```json
{
  "source": "Ghaziabad",
  "destination": "Bengaluru",
  "algorithm": "Dijkstra"
}
```

**Response:**

```json
{
  "algorithm": "DijkstraAlgorithm",
  "path": [
    "Ghaziabad",
    "Noida",
    "Delhi",
    "Jaipur",
    "Ahmedabad",
    "Mumbai",
    "Pune",
    "Hyderabad",
    "Bengaluru"
  ],
  "distance": 2787,
  "executionTime": 1
}
```

### Compare Algorithms

`POST /api/routes/compare`

**Request:**

```json
{
  "source": "Ghaziabad",
  "destination": "Bengaluru"
}
```

The endpoint returns the result of all implemented algorithms.

### Find Nearest City

`POST /api/routes/nearest-city`

**Request:**

```json
{
  "latitude": 28.6692,
  "longitude": 77.4538
}
```

**Response:**

```json
{
  "id": 3,
  "name": "Ghaziabad",
  "latitude": 28.6692,
  "longitude": 77.4538
}
```

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Thymeleaf
- Maven
- Lombok
- HTML/CSS/JavaScript

---

## ▶️ Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/DhruvGargg/smart-route-finder-with-multiple-algorithms.git
cd smart-route-finder-with-multiple-algorithms
```

### 2. Create the MySQL database

```sql
CREATE DATABASE smart_route_optimizer;
```

### 3. Configure database credentials

> Do not commit database credentials.

Set the following environment variables:

```bash
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
```

The application reads these values from `application.properties`.

### 4. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

### 5. Open the application

```
http://localhost:8080
```

---

## ⚠️ Current Limitations

- The application currently supports a predefined set of cities.
- Road data is manually maintained.
- Traffic conditions are not currently considered.
- `travel_time` is reserved for future time-based optimization.
- Execution-time comparisons are not intended to be production-grade benchmarks because the demonstration graph is relatively small.
- A future version could integrate external geocoding and routing APIs.

---

## 🔮 Future Improvements

- Real-time traffic data
- Dynamic road weights
- Fastest-route optimization using `travel_time`
- Integration with geocoding APIs
- Support for arbitrary cities
- Larger real-world road networks
- Route visualization on an interactive map
- Delivery vehicle capacity constraints
- Multi-stop delivery optimization
- Travelling Salesman / Vehicle Routing Problem optimization
- Persistent algorithm benchmarking on large graphs

---

## 🎯 Key Learning Outcomes

This project demonstrates practical implementation of:

- Graph data structures
- Adjacency lists
- BFS
- Dijkstra's shortest-path algorithm
- A* pathfinding
- Priority queues
- Heuristics
- Path reconstruction
- Haversine distance
- Object-oriented design
- Interfaces and polymorphism
- Spring Boot dependency injection
- Spring Data JPA
- REST APIs
- MySQL persistence
- Browser geolocation

---

## 👨‍💻 Author

**Dhruv Garg**
B.Tech CSE (Artificial Intelligence)

GitHub: [https://github.com/DhruvGargg](https://github.com/DhruvGargg)
