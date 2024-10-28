import MovieCard from "components/MovieCard";
import Pagination from "components/Pagination";

export default function Listing() {
  return (
    <>
      <Pagination />

      <div className="container">
        <div className="row">
          <div className="col">
            <MovieCard />
          </div>
          <div className="col">
            <MovieCard />
          </div>
          <div className="col">
            <MovieCard />
          </div>
          <div className="col">
            <MovieCard />
          </div>
        </div>
      </div>
    </>
  );
}
