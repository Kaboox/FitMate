import ExerciseList from "../components/ExerciseList";
import NavbarDesktop from "../components/NavbarDesktop";
import NavbarMobile from "../components/NavbarMobile";

export default function Discover() {
    return (
        <div className="flex gap-8 bg-black w-screen h-screen">
         <NavbarDesktop></NavbarDesktop>
         <ExerciseList></ExerciseList>
        {/* <NavbarMobile></NavbarMobile> */}

         {/* Mobile */}
         <div className="block md:hidden"></div>
        </div>
    )
}