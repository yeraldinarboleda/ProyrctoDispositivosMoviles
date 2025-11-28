import express from "express";
import bcrypt from "bcryptjs";
import { v4 as uuidv4 } from "uuid";
import { db } from "../db.js";

const router = express.Router();

// Registro
router.post("/register", async (req, res) => {
  const { nombre, email, password } = req.body;

  if (!nombre || !email || !password)
    return res.status(400).json({ error: "Faltan datos" });

  const hash = bcrypt.hashSync(password, 10);

  try {
    await db.query(
      "INSERT INTO usuarios (nombre, email, password_hash) VALUES (?, ?, ?)",
      [nombre, email, hash]
    );
    res.json({ message: "Registro exitoso" });
  } catch (err) {
    res.status(500).json({ error: "Error al registrar" });
  }
});

// Login
router.post("/login", async (req, res) => {
  const { email, password } = req.body;

  const [rows] = await db.query(
    "SELECT * FROM usuarios WHERE email = ?",
    [email]
  );

  if (rows.length === 0)
    return res.status(400).json({ error: "Usuario no encontrado" });

  const usuario = rows[0];
  const ok = bcrypt.compareSync(password, usuario.password_hash);

  if (!ok) return res.status(400).json({ error: "Contraseña incorrecta" });

  const token = uuidv4();

  await db.query(
    "INSERT INTO sesiones (usuario_id, token) VALUES (?, ?)",
    [usuario.id, token]
  );

  res.json({ token });
});

export default router;
