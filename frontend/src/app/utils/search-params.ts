import { HttpParams } from "@angular/common/http"

export interface SearchParamsFields {
    page?: number,
    size?: number,
    artists?: string[],
    locations?: string[],
    before?: Date,
    after?: Date,
    showPast?: boolean,
    priceLowerThan?: number,
    priceHigherThan?: number
}

export class SearchParams {
    page: number = 0
    size: number
    artists?: string
    locations?: string
    before?: string
    after?: string
    showPast?: boolean
    priceLowerThan?: number
    priceHigherThan?: number

    constructor(fields: SearchParamsFields) {
        if (fields.page) this.page = fields.page;
        fields.size ? this.size = fields.size : this.size = 6;
        if (fields.artists) this.artists = fields.artists.toString();
        if (fields.locations) this.locations = fields.locations.toString();
        if (fields.before) this.before = fields.before.toISOString().replace('Z', '');
        if (fields.after) this.after = fields.after.toISOString().replace('Z', '');
        if (fields.showPast) this.showPast = fields.showPast;
        if (fields.priceLowerThan) this.priceLowerThan = fields.priceLowerThan;
        if (fields.priceHigherThan) this.priceHigherThan = fields.priceHigherThan;

    }

    toHttpParams(): HttpParams {
        let params = new HttpParams();

        params = params.append("page", this.page);
        params = params.append("size", this.size.toString());
        if (this.artists) params =  params.append("artists", this.artists);
        if (this.locations) params = params.append("locations", this.locations);
        if (this.before) params = params.append("before", this.before);
        if (this.after) params = params.append("after", this.after);
        if (this.showPast) params = params.append("showPast", this.showPast);
        if (this.priceLowerThan) params = params.append("priceLowerThan", this.priceLowerThan);
        if (this.priceHigherThan) params = params.append("priceHigherThan", this.priceHigherThan);

        return params;
    }
}
