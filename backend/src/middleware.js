import { db } from "./db.js";

export const authMiddleware = async (req, res, next) => {
  let token = req.headers.authorization || "";

// Si llega como "Bearer xxxx", extraer solo el token
if (token.startsWith("Bearer ")) {
  token = token.split(" ")[1];
}

  if (!token) return res.status(401).json({ error: "Token requerido" });

  const [rows] = await db.query(
    "SELECT * FROM sesiones WHERE token = ?",
    [token]
  );

  if (rows.length === 0)
    return res.status(401).json({ error: "Token inválido" });

  req.usuario_id = rows[0].usuario_id;

  next();
};
