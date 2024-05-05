import { Concert } from "../models/concert.model";
import { Ticket } from "../models/ticket.model";

export const convertDatetime = (concerts: Concert[]): Concert[] => {
    concerts.forEach((concert) => {
        concert.datetime = new Date(concert.datetime);
    })
    return concerts;
}

export const convertTicketDatetime = (tickets: Ticket[]): Ticket[] => {
    tickets.forEach((ticket) => {
        ticket.concert.datetime = new Date(ticket.concert.datetime);
    })
    return tickets;
}

export const formatHour = (date: Date): string => {
    let str = date.getHours().toString() + ":";
    date.getMinutes() < 10
        ? str += "0" + date.getMinutes()
        : str += date.getMinutes();

    return str;
}
