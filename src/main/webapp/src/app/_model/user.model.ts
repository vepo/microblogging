export interface Profile {
    id: number;
    handle: string;
    images: ProfileImages;
    createdAt: Date;
}

export interface ProfileImages {
    cover: Image;
    avatar: Image;
}

export interface Image {
    url: string;
}

export interface User {
    id: number,
    handle: string,
    roles: string[]
}

export interface LoginResponse {
    id: number,
    handle: string,
    email: string,
    accessToken: string
}

export interface Credentials {
    handle: string,
    password: string
}

export interface RegisterRequest {
    handle: string,
    email: string,
    password: string
}