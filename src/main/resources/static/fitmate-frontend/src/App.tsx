import { NavbarProvider } from "./context/NavbarContext"
import Discover from "./pages/Discover"


function App() {

  return (
    <>
      <NavbarProvider>
        <Discover></Discover>
      </NavbarProvider>
    </>
  )
}

export default App
