export class UserUpdateDto {
    email: string;
    name: string;
    picture: string;

    constructor(e: string, n: string, p: string) {
        this.email = e;
        this.name = n;
        this.picture = p;
    }
}
