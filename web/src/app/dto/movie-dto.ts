export class MovieDto {
    title: string;
    genre: string;
    synopsis: string;
    trailer: string;

    constructor(t: string, g: string, s: string, tr: string) {
        this.title = t;
        this.genre = g;
        this.synopsis = s;
        this.trailer = tr;
    }
}
