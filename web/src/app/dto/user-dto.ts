export class UserDto {
    email: string;
    password: string;
    name: string;

    constructor(e: string, p: string, n: string) {
        this.email = e;
        this.password = p;
        this.name = n;
    }
}
