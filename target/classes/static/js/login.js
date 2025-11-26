document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
  const role = document.getElementById("role").value;

  // Puedes reemplazar esto con lógica real desde tu backend
  if (role === "admin" && email === "admin@skypers.com" && password === "admin123") {
    sessionStorage.setItem("rol","admin");
    window.location.href = "views/admin.html";
  } else if (role === "cliente" && email === "cliente@skypers.com" && password === "cliente123") {
    localStorage.setItem("clienteEmail", email);
    window.location.href = "cliente.html";
  } else {
    alert("Credenciales incorrectas.");
  }
});
