const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN':
      return {
        username: action.payload.username,
        isAuthenticated: true,
        token: action.payload.token,
      };
    case 'LOGOUT':
      return {
        username: null,
        isAuthenticated: false,
        token: null,
      };
    default:
      return state;
  }
};

export default authReducer;
