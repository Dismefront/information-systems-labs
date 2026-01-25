import { ApiClient } from './api-client';

export interface LoginRequest {
    username: string;
    password: string;
}

export interface RegisterRequest {
    username: string;
    password: string;
}

export interface User {
    username: string;
    roles: string[];
}

export class AuthService {
    static async login(credentials: LoginRequest): Promise<User> {
        return ApiClient.post<User>('/api/login', credentials);
    }

    static async register(userData: RegisterRequest): Promise<User> {
        return ApiClient.post<User>('/api/register', userData);
    }

    static async logout(): Promise<void> {
        return ApiClient.post('/api/logout');
    }

    static async getCurrentUser(): Promise<User> {
        return ApiClient.get<User>('/api/current-user');
    }
}