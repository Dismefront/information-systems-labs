import { API_ENDPOINT } from '@/App';

export interface ApiResponse<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    number: number;
    size: number;
}

export interface ApiError {
    message: string;
    status: number;
}

export class ApiClient {
    private static async request<T>(
        url: string,
        options: RequestInit = {}
    ): Promise<T> {
        const response = await fetch(`${API_ENDPOINT}${url}`, {
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                ...options.headers,
            },
            ...options,
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP ${response.status}: ${errorText || response.statusText}`);
        }

        return response.json();
    }

    static async get<T>(url: string): Promise<T> {
        return this.request<T>(url, { method: 'GET' });
    }

    static async post<T>(url: string, data?: any): Promise<T> {
        return this.request<T>(url, {
            method: 'POST',
            body: data ? JSON.stringify(data) : undefined,
        });
    }

    static async put<T>(url: string, data?: any): Promise<T> {
        return this.request<T>(url, {
            method: 'PUT',
            body: data ? JSON.stringify(data) : undefined,
        });
    }

    static async delete<T>(url: string): Promise<T> {
        return this.request<T>(url, { method: 'DELETE' });
    }
}