type ExerciseCardProps = {
  name: string;
  category: string;
  description: string;
  imageUrl: string;
};

export default function ExerciseCard({
  name,
  category,
  description,
  imageUrl,
}: ExerciseCardProps) {
  return (
    <div className="max-w-xl bg-neutral-800 text-white rounded-xl shadow-md p-4 flex gap-4 hover:bg-neutral-700 transition">
      {/* Miniatura ćwiczenia */}
      <img
        src={imageUrl}
        alt={name}
        className="w-24 h-24 object-cover rounded-lg"
      />

      {/* Tekst */}
      <div className="flex flex-col justify-between flex-1">
        <div>
          <h3 className="text-lg font-semibold">{name}</h3>
          <p className="text-sm text-green-400">{category}</p>
          <p className="text-xs text-gray-300 mt-2 line-clamp-2">{description}</p>
        </div>

        <button className="mt-2 text-sm text-green-400 hover:underline self-start">
          Szczegóły
        </button>
      </div>
    </div>
  );
}
