const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN':
      return {
        username: action.payload.username,
        isAuthenticated: true,
      };
    case 'LOGOUT':
      return {
        username: null,
        isAuthenticated: false,
      };
    default:
      return state;
  }
};

export default authReducer;
