import express from "express";
import { db } from "../db.js";
import { authMiddleware } from "../middleware.js";

const router = express.Router();

// Obtener todas las tareas del usuario
router.get("/", authMiddleware, async (req, res) => {
  const [rows] = await db.query(
    "SELECT * FROM tareas WHERE usuario_id = ?",
    [req.usuario_id]
  );
  res.json(rows);
});

// Crear tarea
router.post("/", authMiddleware, async (req, res) => {
  const { titulo, descripcion, fecha_limite, estado } = req.body;

  await db.query(
    `INSERT INTO tareas (usuario_id, titulo, descripcion, fecha_limite, estado)
     VALUES (?, ?, ?, ?, ?)`,
    [req.usuario_id, titulo, descripcion, fecha_limite, estado || "pendiente"]
  );

  res.json({ message: "Tarea creada" });
});

// Editar tarea
router.put("/:id", authMiddleware, async (req, res) => {
  const { titulo, descripcion, fecha_limite, estado } = req.body;

  await db.query(
    `UPDATE tareas SET titulo = ?, descripcion = ?, fecha_limite = ?, estado = ?
     WHERE id = ? AND usuario_id = ?`,
    [titulo, descripcion, fecha_limite, estado, req.params.id, req.usuario_id]
  );

  res.json({ message: "Tarea actualizada" });
});

// Eliminar tarea
router.delete("/:id", authMiddleware, async (req, res) => {
  await db.query(
    "DELETE FROM tareas WHERE id = ? AND usuario_id = ?",
    [req.params.id, req.usuario_id]
  );

  res.json({ message: "Tarea eliminada" });
});

export default router;
