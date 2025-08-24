import NavbarDesktop from "../components/NavbarDesktop";

export default function Discover() {
    return (
        <div className="bg-black w-screen h-screen">
         <NavbarDesktop></NavbarDesktop>


         {/* Mobile */}
         <div className="block md:hidden"></div>
        </div>
    )
}