import { useState } from "react";
import { Link } from "react-router-dom";

export default function Login() {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!form.email.trim() || !form.password.trim()) {
      alert("Please fill in all fields.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: form.email,
          password: form.password,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        alert(`Login failed: ${errorData.message || 'Unknown error'}`);
        console.error("Login error:", errorData);
        return;
      }

      const data = await response.json();
      console.log("Login successful:", data);

      localStorage.setItem("token", data.token);
      localStorage.setItem("user", JSON.stringify({
        email: data.email,
        name: data.name,
        role: data.role,
      }))
    
      window.location.href = "/";


    }
    catch (error) {
      console.error("Error during login:", error);
      alert("An error occurred. Please try again.");
    }

    alert("Login submitted!");
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-neutral-900">
      <form
        onSubmit={handleSubmit}
        className="bg-neutral-800 p-8 rounded-lg shadow-md w-full max-w-md flex flex-col gap-4"
      >
        <h2 className="text-2xl font-bold text-white mb-4 text-center">Sign In</h2>
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          className="p-2 rounded bg-neutral-700 text-white focus:outline-none"
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          className="p-2 rounded bg-neutral-700 text-white focus:outline-none"
          required
        />
        <button
          type="submit"
          className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 rounded mt-2"
        >
          Login
        </button>

        <div className="flex gap-2 text-gray-300">
          <p>Don't have an account?</p> <Link to="/register" className="text-green-600 hover:text-green-700 cursor-pointer">Create account</Link>  
        </div> 
        <div className="flex gap-2 text-gray-300">
          <p>Go back to</p> <Link to="/" className="text-green-600 hover:text-green-700 cursor-pointer">Main page</Link>  
        </div> 
      </form>
    </div>
  );
}