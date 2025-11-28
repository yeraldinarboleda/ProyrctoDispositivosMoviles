import { db } from "./db.js";

async function testConnection() {
  try {
    const [rows] = await db.query("SELECT 1 + 1 AS resultado");
    console.log("Conexión exitosa:", rows);
  } catch (err) {
    console.error("Error conectando a MySQL:", err);
  }
}

testConnection();
