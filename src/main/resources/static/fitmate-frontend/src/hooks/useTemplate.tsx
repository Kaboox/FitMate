import { useContext } from "react";
import { TemplateContext } from "../context/TemplateContext";

export function useTemplate() {
  const ctx = useContext(TemplateContext);
  if (!ctx) throw new Error("useTemplate must be used within TemplateProvider");
  return ctx;
}
