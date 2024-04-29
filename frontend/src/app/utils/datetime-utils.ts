import { Concert } from "../models/concert.model";

export const convertDatetime = (concerts: Concert[]): Concert[] => {
    concerts.forEach((concert) => {
        concert.datetime = new Date(concert.datetime);
    })
    return concerts;
}

export const formatHour = (date: Date): string => {
    let str = date.getHours().toString() + ":";
    date.getMinutes() < 10
        ? str += "0" + date.getMinutes()
        : str += date.getMinutes();

    return str;
}
