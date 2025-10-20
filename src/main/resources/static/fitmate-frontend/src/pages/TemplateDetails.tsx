import { useParams } from "react-router-dom";
import { useTemplate } from "../context/TemplateContext";
import { useEffect } from "react";

export default function TemplateDetails() {
    const { id } = useParams<{ id: string }>();
    const { templateDetails, fetchTemplateDetails } = useTemplate();

    useEffect(() => {
        if (id) {
            fetchTemplateDetails(Number(id));
        }
    }, [id]);



    return <div className="flex flex-col md:flex-row bg-black w-full min-h-screen text-white">
        <h1>{templateDetails?.name}</h1>
    </div>;
}