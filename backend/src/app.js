import express from "express";
import cors from "cors";
import authRoutes from "./routes/auth.js";
import tareasRoutes from "./routes/tareas.js";

const app = express();

app.use(cors());
app.use(express.json());

// Rutas
app.use("/api", authRoutes);
app.use("/api/items", tareasRoutes);

// Server
const PORT = 3000;
app.listen(PORT, "0.0.0.0", () => {
    console.log("API funcionando en puerto " + PORT);
});
