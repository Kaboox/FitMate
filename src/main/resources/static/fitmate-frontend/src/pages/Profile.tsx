import { Undo } from "lucide-react";
import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useUser } from "../context/UserContext";

export default function Profile() {
  const { user, setUser, refreshUser } = useUser();
  const [form, setForm] = useState({
    username: "",
    password: "",
    confirm_password: "",
    avatarUrl: "",
  });
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const fileInputRef = useRef<HTMLInputElement | null>(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      navigate("/login");
      return;
    }

    fetch("http://localhost:8080/users/me", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .then((data) => {
        setUser(data);
        setForm({
          username: data.username,
          password: "",
          confirm_password: "",
          avatarUrl: data.avatarUrl || "",
        });
        setLoading(false);
      })
      .catch((err) => console.error(err));
  }, [token]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Modifying profile details
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    let passwordChange = 0;
    if (!token) return;

    const payload: any = {};
    if (form.username.trim()) payload.username = form.username.trim();
    if (form.password.trim()) {
      if (form.password !== form.confirm_password) {
        alert("Passwords do not match");
        return;
      }
      payload.password = form.password;
      passwordChange = 1;
    }
    if (form.avatarUrl.trim()) payload.avatarUrl = form.avatarUrl.trim();

    try {
      const res = await fetch("http://localhost:8080/users/profile", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) throw new Error("Update failed");

      const updated = await res.json();
      setUser(updated);
      alert("Profile updated!");
      if (passwordChange) {
        alert("Password changed, please log in again.");
        localStorage.removeItem("token");
        setUser(null);
        window.location.href = "/login";
      } else {
        refreshUser();
      }
    } catch (error) {
      console.error(error);
      alert("Error updating profile");
    }
  };

  const handleAvatarClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files || e.target.files.length === 0) return;
    const file = e.target.files[0];

    if (!token) return;

    const formData = new FormData();
    formData.append("avatar", file);

    try {
      const res = await fetch("http://localhost:8080/users/avatar", {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: formData,
      });

      if (!res.ok) {
        const err = await res.json().catch(() => ({}));
        alert("Upload failed: " + (err.error || res.statusText));
        return;
      }

      const updated = await res.json();
      setUser(updated);
      setForm((prev) => ({ ...prev, avatarUrl: updated.avatarUrl }));
    } catch (error) {
      console.error(error);
      alert("Error uploading avatar");
    }
  };

  if (loading)
    return (
      <div className="flex items-center justify-center min-h-screen text-white">
        Loading...
      </div>
    );

  return (
    <div className="flex flex-col items-center bg-neutral-900 min-h-screen text-white">
      {/* Hero */}
      <div className="relative w-full h-64 md:h-80 flex flex-col items-center justify-center bg-neutral-800">
        <img
          src={user?.avatarUrl || "https://via.placeholder.com/150"}
          alt={user?.username}
          className="w-32 h-32 rounded-full border-4 border-green-600 object-cover cursor-pointer"
          onClick={handleAvatarClick}
        />
        <input
          type="file"
          ref={fileInputRef}
          className="hidden"
          accept="image/*"
          onChange={handleFileChange}
        />
        <h1 className="text-3xl md:text-5xl font-bold mt-4">
          {user?.username}
        </h1>
        <p className="text-gray-300">{user?.email}</p>
        <p
          className="absolute top-4 left-4 flex items-center gap-2 text-xl md:text-2xl font-mono text-white cursor-pointer hover:text-green-400 transition"
          style={{ textShadow: "2px 2px 6px rgba(0,0,0,0.8)" }}
          onClick={() => navigate(-1)}
        >
          <Undo size={24} />
          FitMate
        </p>
      </div>

      {/* Update form */}
      <form
        onSubmit={handleSubmit}
        className="bg-neutral-800 p-8 rounded-lg shadow-md w-full max-w-md flex flex-col gap-4 mt-6"
      >
        <h2 className="text-2xl font-bold text-white mb-4 text-center">
          Edit Profile
        </h2>

        <input
          type="text"
          name="username"
          placeholder={user?.username || "Username"}
          value={form.username}
          onChange={handleChange}
          className="p-2 rounded bg-neutral-700 text-white focus:outline-none"
          required
        />

        <input
          type="password"
          name="password"
          placeholder="New Password"
          value={form.password}
          onChange={handleChange}
          className="p-2 rounded bg-neutral-700 text-white focus:outline-none"
        />

        <input
          type="password"
          name="confirm_password"
          placeholder="Confirm New Password"
          value={form.confirm_password}
          onChange={handleChange}
          className="p-2 rounded bg-neutral-700 text-white focus:outline-none"
        />

        <button
          type="submit"
          className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 rounded mt-2"
        >
          Save Changes
        </button>
      </form>
    </div>
  );
}
