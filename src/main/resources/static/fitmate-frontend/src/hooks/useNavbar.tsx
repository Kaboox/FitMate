import { useContext } from "react";
import { NavbarContext } from "../context/NavbarContext";

export function useNavbar() {
  const ctx = useContext(NavbarContext);
  if (!ctx) throw new Error("useNavbar must be used within NavbarProvider");
  return ctx;
}
