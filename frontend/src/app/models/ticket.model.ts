import { Concert } from "./concert.model";
import { User } from "./user.model";

export interface Ticket{
    id: number,
    num_ticket: number,
    user: User,
    concert: Concert
}