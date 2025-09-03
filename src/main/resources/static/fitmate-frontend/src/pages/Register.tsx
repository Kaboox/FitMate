import { useState } from "react";

export default function Register() {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Add registration logic here
    alert("Registration submitted!");
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-neutral-900">
      <form
        onSubmit={handleSubmit}
        className="bg-neutral-800 p-8 rounded-lg shadow-md w-full max-w-md flex flex-col gap-4"
      >
        <h2 className="text-2xl font-bold text-white mb-4 text-center">Create Account</h2>
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
        <input
          type="password"
          name="confirmPassword"
          placeholder="Confirm Password"
          value={form.confirmPassword}
          onChange={handleChange}
          className="p-2 rounded bg-neutral-700 text-white focus:outline-none"
          required
        />
        <button
          type="submit"
          className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 rounded mt-2"
        >
          Register
        </button>

        <p>Already have an account? <span>Log in</span></p>
      </form>
    </div>
  );
}