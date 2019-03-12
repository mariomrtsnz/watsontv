export class MovieDto {
    title: string;
    coverImage: string;
    genre: string;
    synopsis: string;
    trailer: string;

    constructor(t: string, cI: string, g: string, s: string, tr: string) {
        this.title = t;
        this.coverImage = cI;
        this.genre = g;
        this.synopsis = s;
        this.trailer = tr;
    }
}
