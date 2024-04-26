import { Artist } from "./artist.model";
import { Genre } from "./genre.model";

export interface Concert {
    id: number,
    datetime: Date,
    place: string,
    num_tickets: number,
    price: number,
    info: string,
    artist: Artist,
    genre: Genre,
}
