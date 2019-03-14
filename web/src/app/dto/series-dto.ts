export class SeriesDto {
    title: string;
    genre: string;
    synopsis: string;
    broadcaster: string;
    airsDayOfWeek: number;

    constructor(t: string, g: string, s: string, b: string, airsDOW: number) {
        this.title = t;
        this.genre = g;
        this.synopsis = s;
        this.broadcaster = b;
        this.airsDayOfWeek = airsDOW;
    }
}
