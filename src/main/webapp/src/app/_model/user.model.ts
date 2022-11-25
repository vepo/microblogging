export interface User {
    username: string,
    roles: string[]
}

export interface Credentials {
    username: string,
    password: string
}

export interface RegisterRequest {
    username: string,
    email: string,
    password: string
}