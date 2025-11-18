import ExerciseList from "../components/ExerciseList";
import NavbarDesktop from "../components/NavbarDesktop";
import NavbarMobile from "../components/NavbarMobile";

export default function Discover() {
  return (
    <div className="flex flex-col md:flex-row gap-8 bg-black w-full h-full min-h-dvh">
      <div className="hidden md:flex w-1/12 min-w-30">
        <NavbarDesktop></NavbarDesktop>
      </div>
      <div className="flex md:hidden w-full sticky top-0 z-50">
        <NavbarMobile></NavbarMobile>
      </div>

      <ExerciseList></ExerciseList>
    </div>
  );
}
